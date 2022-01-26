class Util {
    fun getRandomNum(maxNum: Integer): Double {
        return Math.floor(Math.random() * maxNum)
    }

    fun getRandomInt(maxNum: Integer): Int {
        return getRandomNum(maxNum).toInt()
    }
}