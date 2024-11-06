package org.jetbrains.edu.buildingBlocks

data class Person(val firstName: String, val lastName: String)

interface ImportantThing {
    val name: String
    val version: Int
    val responsiblePeople: List<Person>
    
    val presentableName: String
        get() = "$name:$version"
    
    operator fun contains(person: Person): Boolean = person in responsiblePeople
    
    operator fun plus(person: Person) = ImportantThing(name, responsiblePeople + person, this.version + 1)
    
    operator fun component1() = name
    
    operator fun component2() = responsiblePeople.firstOrNull()?.firstName

    companion object ImportantThingBuilder {
        operator fun invoke(
            name: String,
            responsiblePeople: List<Person>,
            version: Int = 0
        ) = object : ImportantThing {
            override val name: String = name
            override val version: Int = version
            override val responsiblePeople: List<Person> = responsiblePeople
        }
    }
}

fun printResponsibility(person: Person, thing: ImportantThing) {
    if (person in thing) {
        println("${person.firstName} is responsible for ${thing.presentableName}")
    } else {
        println("${person.firstName} isn't responsible for ${thing.presentableName}")
    }
}

fun operatorsExample() {
    val ryan = Person("Ryan", "Gosling")
    val emma = Person("Emma", "Stone")
    var thing = ImportantThing("Driving", listOf(ryan))
    printResponsibility(ryan, thing)
    printResponsibility(emma, thing)
    thing += emma
    printResponsibility(ryan, thing)
    printResponsibility(emma, thing)
    val (name, firstPerson) = thing
    println("The first person responsible for $name is called $firstPerson")
}
