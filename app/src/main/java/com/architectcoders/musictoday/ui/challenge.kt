package com.architectcoders.musictoday.ui

// Propiedad intelectual= Esta prueba fue desarrollada por TCIT el año 2014 y registrada bajo propiedad intelectual, cualquier publicación o difamación podría estar sujeta a acciones legales. Hay otras empresas que nos han copiado esta prueba, no aceptes imitaciones, exige el original xD

// No realizar la prueba en repl.it al hacerlo su respuesta queda disponible para otros postulantes, tampoco subirla a repositorios de github públicos

// No editar clases distintas al Challenge o propiedades privadas dentro de la clase Challenge, los tipos de retorno de cada función los define en el desarrollo

// Utilizar openjdk 18

class Client(
    val id: Int, val taxNumber: String, val name: String
) {
    override fun toString(): String {
        return "ID: $id, RUT: $taxNumber, Name: $name"
    }
}

open class Bank(
    val id: Int,
    val name: String,
) {
    override fun toString(): String {
        return "ID: $id, Name: $name"
    }
}

class Account(
    val clientId: Int,
    val bankId: Int,
    val balance: Int,
) {
    override fun toString(): String {
        return "ClientID: $clientId, BankId: $bankId, balance: $balance"
    }
}

class Challenge {
    private val banks = listOf(
        Bank(1, "SANTANDER"),
        Bank(2, "CHILE"),
        Bank(3, "ESTADO"),
    )

    private val clients = listOf(
        Client(1, "86620855", "HECTOR ACUÑA BOLAÑOS"),
        Client(2, "7317855K", "JESUS RODRIGUEZ ALVAREZ"),
        Client(3, "73826497", "ANDRES NADAL MOLINA"),
        Client(4, "88587715", "SALVADOR ARNEDO MANRIQUEZ"),
        Client(5, "94020190", "VICTOR MANUEL ROJAS LUCAS"),
        Client(6, "99804238", "MOHAMED FERRE SAMPER"),
    )

    private var accounts = listOf(
        Account(6, 1, 15000),
        Account(1, 3, 18000),
        Account(5, 3, 135000),
        Account(2, 2, 5600),
        Account(3, 1, 23000),
        Account(5, 2, 15000),
        Account(3, 3, 45900),
        Account(2, 3, 19000),
        Account(4, 3, 51000),
        Account(5, 1, 89000),
        Account(1, 2, 1600),
        Account(5, 3, 37500),
        Account(6, 1, 19200),
        Account(2, 3, 10000),
        Account(3, 2, 5400),
        Account(3, 1, 9000),
        Account(4, 3, 13500),
        Account(2, 1, 38200),
        Account(5, 2, 17000),
        Account(1, 3, 1000),
        Account(5, 2, 600),
        Account(6, 1, 16200),
        Account(2, 2, 10000),
    )

    // 0 Arreglo con los ids de clientes
    fun listClientIds() = clients.map { it.id }


    // 1 Arreglo con los ids de clientes ordenados por rut
    fun listClientsIdsSortByTaxNumber() = clients.sortedBy { it.taxNumber }.map { it.id }

    // 2 Arreglo con los nombres de cliente ordenados de mayor a menor por la suma TOTAL de los saldos de cada cliente en los bancos que participa.
    fun sortClientsTotalBalances(): List<String> = accounts.asSequence().sortedBy { it.clientId }
        .groupBy { it.clientId }
        .map { g -> with(g.value[0]){
                return@with Account(
                    clientId = clientId,
                    balance  = g.value.sumOf { it.balance },
                    bankId   = bankId
                )
            }
        }
        .sortedBy {it.balance }
        .map { e -> clients[e.clientId-1].name }
        .toList()

    // 3 Objeto en que las claves sean los nombres de los bancos y los valores un arreglo con los ruts de sus clientes ordenados alfabéticamente por nombre.
    fun banksClientsTaxNumbers() = accounts.sortedBy { it.clientId }.groupBy { it.bankId }.map { g ->
            mapOf(banks[g.key-1].name to g.value.map {
                clients.sortedBy { c -> c.name }[it.clientId-1].taxNumber
            })
    }

    // 4 Arreglo ordenado decrecientemente con los saldos de clientes que tengan más de 25.000 en el Banco SANTANDER
    fun richClientsBalances() {
        // CODE HERE
    }

    // 5 Arreglo con ids de bancos ordenados crecientemente por la cantidad TOTAL de dinero que administran.
    fun banksRankingByTotalBalance() {
        // CODE HERE
    }

    // 6 Objeto en que las claves sean los nombres de los bancos y los valores el número de clientes que solo tengan cuentas en ese banco.
    fun banksFidelity() {
        // CODE HERE
    }

    // 7 Objeto en que las claves sean los nombres de los bancos y los valores el id de su cliente con menos dinero.
    fun banksPoorClients() {
        // CODE HERE
    }

    /*
        8 Agregar nuevo cliente con datos ficticios a "clientes" y agregar una cuenta en el BANCO ESTADO con un saldo de 9000 para este nuevo empleado.
        Luego devolver el lugar que ocupa este cliente en el ranking de la pregunta 2.
        No modificar arreglos originales para no alterar las respuestas anteriores al correr la solución
    */
    fun newClientRanking() {
        // CODE HERE
    }
}

fun main() {

    val challenge = Challenge()

    println("Pregunta 1")
    val p0 = challenge.listClientIds()
    println(p0)

    println("Pregunta 2")
    val p1 = challenge.listClientsIdsSortByTaxNumber()
    println(p1)

    println("Pregunta 3")
    val p2 = challenge.sortClientsTotalBalances()
    println(p2)

    println("Pregunta 4")
    val p3 = challenge.banksClientsTaxNumbers()
    println(p3)

    println("Pregunta 5")
    val p4 = challenge.richClientsBalances()
    println(p4)

    println("Pregunta 6")
    val p5 = challenge.banksRankingByTotalBalance()
    println(p5)

    println("Pregunta 7")
    val p6 = challenge.banksFidelity()
    println(p6)


    println("Pregunta 8")
    val p7 = challenge.banksPoorClients()
    println(p7)

    println("Pregunta 9")
    val p8 = challenge.newClientRanking()
    println(p8)

}




