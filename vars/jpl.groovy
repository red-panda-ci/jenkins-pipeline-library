#!groovy

class jpl implements Serializable {
    private String name
    def setName(value) {
        name = value
    }
    def getName() {
        name
    }
    def caution(message) {
        println("Hello, ${name}! CAUTION: ${message}")
    }
}
