class Domain<T>(var `val`: T) : Comparable<Domain<*>?> where T : Number?, T : Comparable<T>? {
    @Override
    override fun hashCode(): Int {
        return if (`val` != null) `val`!!.hashCode() else 0
    }

    @Override
    operator fun compareTo(state: Domain<*>): Int {
        return `val`!!.compareTo(state.`val` as T)
    }

    @Override
    override fun equals(obj: Object): Boolean {
        if (this === obj) return true
        return if (obj !is Domain<*>) false else `val` != null && `val`!!.equals((obj as Domain<*>).`val`)
    }

    fun intValue(): Integer {
        return `val` as Integer
    }

    fun longValue(): Long {
        return `val` as Long
    }

    fun floatValue(): Float {
        return `val` as Float
    }

    fun doubleValue(): Double {
        return `val` as Double
    }

    @Override
    override fun toString(): String {
        return `val`.toString()
    }
}