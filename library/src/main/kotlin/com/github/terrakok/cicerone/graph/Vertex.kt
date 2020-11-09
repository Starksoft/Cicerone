package com.github.terrakok.cicerone.graph

import com.github.terrakok.cicerone.*

open class Vertex internal constructor(
    val id: String,
    val edges: Set<Vertex>,
    val jumps: Set<Jump>,
    val destroyPreviousView: Boolean = true,
    val screenFactory: (id: String) -> Screen? = { null }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

internal class VertexLink(
    id: String,
    destroyPreviousView: Boolean = true
): Vertex(id, emptySet(), emptySet(), destroyPreviousView)

data class Jump(
    val id: String,
    val reusePreviousVertexes: Boolean
)

//Graph DSL

class GraphInfo(
    var edges: MutableSet<Vertex>.() -> Unit = {},
    var jumps: MutableSet<Jump>.() -> Unit = {}
)

fun graph(
    setup: GraphInfo.() -> Unit
): Vertex {
    val info = GraphInfo().apply(setup)
    return Vertex(
        "root",
        mutableSetOf<Vertex>().apply(info.edges),
        mutableSetOf<Jump>().apply(info.jumps)
    )
}

class VertexInfo(
    var edges: MutableSet<Vertex>.() -> Unit = {},
    var jumps: MutableSet<Jump>.() -> Unit = {},
    var screen: (id: String) -> Screen? = { null }
)

fun MutableSet<Vertex>.dest(
    id: String,
    destroyPreviousView: Boolean = true,
    setup: VertexInfo.() -> Unit = {}
) {
    val info = VertexInfo().apply(setup)
    add(Vertex(
        id,
        mutableSetOf<Vertex>().apply(info.edges),
        mutableSetOf<Jump>().apply(info.jumps),
        destroyPreviousView,
        info.screen
    ))
}

fun MutableSet<Vertex>.edge(
    id: String,
    destroyPreviousView: Boolean = true
) {
    add(VertexLink(id, destroyPreviousView))
}

fun MutableSet<Jump>.jump(
    id: String,
    reusePreviousVertexes: Boolean = true
) {
    add(Jump(id, reusePreviousVertexes))
}