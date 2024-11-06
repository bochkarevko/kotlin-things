package org.jetbrains.edu.buildingBlocks

import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import kotlin.reflect.KProperty
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface Describable {
    val description: String
}

@OptIn(ExperimentalUuidApi::class)
interface Identifiable {
    val id: Uuid
    
    companion object IdSource {
        private val ids = ConcurrentHashMap<String, Uuid>()
        
        operator fun getValue(thisRef: Any, property: KProperty<*>): Uuid {
            val className = thisRef::class.qualifiedName ?: "LOCAL"
            return ids.getOrPut("$className:${property.name}") { Uuid.random() }
        }
    }
}

class ImpossibleThing(private val essentialData: EssentialData) :
    ImportantThing by ImportantThing(essentialData.name, emptyList(), essentialData.version), 
    Describable, Identifiable {
    override operator fun plus(person: Person) = this

    override val description by essentialData::description
    
    @OptIn(ExperimentalUuidApi::class)
    override val id by Identifiable.IdSource
}

@OptIn(ExperimentalUuidApi::class)
fun delegationExample() {
    var thing = ImpossibleThing(EssentialData("Return to the year 2007", 1))
    println("Retrieve ${thing.description} id 10 times:")
    repeat(10) {
        println("\t${thing.id}")
        thing += Person("John ${Random.nextInt(1, 5)}", "Doe")
    }
}
