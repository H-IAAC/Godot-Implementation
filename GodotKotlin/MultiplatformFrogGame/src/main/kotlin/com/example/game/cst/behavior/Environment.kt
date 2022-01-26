import java.util.ArrayList
import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject

abstract class Environment(actionSpace: Array<Domain>, codelets: ArrayList<Codelet?>,
                           memoryObjects: ArrayList<MemoryObject?>, displayGame: Boolean) {
    // (min value, max value, step: only if is discrete, type: 0 - Discrete, 1 - Continuous)
    protected var actionSpace: Array<Domain>
    protected var displayGame: Boolean

    // faz sentido ter codelets?
    protected var codelets: ArrayList<Codelet>

    // faz sentido NAO ter memoryObjects?
    protected var memoryObjects: ArrayList<MemoryObject>

    // o espaco de observacao nao seria o próprio conjunto de dados acessados pelso objetos de memória?
    protected var observationSpace: ArrayList<Domain>? = null

    init {
        // this.numDim = numDim;
        this.actionSpace = actionSpace
        this.displayGame = displayGame
        this.codelets = codelets
        this.memoryObjects = memoryObjects
    }

    fun getObservationSpace(): ArrayList<Domain> {
        return observationSpace
    }

    fun getActionSpace(): Array<Domain> {
        return actionSpace
    }

    // extract information of Codelets to update enviroment.
    abstract fun accessCodelets()

    // extract information of Memory Objects to update enviroment.
    abstract fun accessMemoryObjects()

    abstract fun proc()

    // returns the new observation space at the end of the function.
    abstract fun reset(): ArrayList<Domain?>?

    // returns {observationSpace : ArrayList<Domain>, reward : Double, done : boolean, info : String}
    abstract fun step(action: Domain?): ArrayList?

    // depending of your problem, you might want to display your environment.
    abstract fun render()
}