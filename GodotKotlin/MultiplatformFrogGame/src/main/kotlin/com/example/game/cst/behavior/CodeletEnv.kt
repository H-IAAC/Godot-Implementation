import java.util.ArrayList
import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject

class CodeletEnv(actionSpace: Array<Domain>, codelets: ArrayList<Codelet?>,
                           memoryObjects: ArrayList<MemoryObject?>, displayGame: Boolean) : Codelet() {
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

    fun proc() [
        TODO("to do")
    ]
    // extract information of Codelets to update enviroment.
    fun accessCodelets() {
        return codelets
    }

    // extract information of Memory Objects to update enviroment.
    abstract fun accessMemoryObjects() {
        return memoryObjects
    }

    // returns the new observation space at the end of the function.
    abstract fun reset(): ArrayList<Domain?>?

    // returns {observationSpace : ArrayList<Domain>, reward : Double, done : boolean, info : String}
    abstract fun step(action: Domain?): ArrayList?

    // depending of your problem, you might want to display your CodeletEnv.
    abstract fun render()
}