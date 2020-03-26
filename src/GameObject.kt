
import gameobjects.Background
import vision.gears.webglmath.Mat4
import vision.gears.webglmath.UniformProvider
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3

open class GameObject(
        mesh : Mesh,
        val position : Vec3 = Vec3.zeros.clone(),
        var roll : Float = 0.0f,
        val scale : Vec3 = Vec3.ones.clone()
) : UniformProvider("gameObject") {

    val modelMatrix by Mat4()
    val offset by Vec2()
    val size by Vec2(1.0f, 1.0f)

    init {
        addComponentsAndGatherUniforms(mesh)
    }

    open fun interact(
            dt : Float = 0.016666f,
            t : Float = 0.0f,
            keysPressed : Set<String> = emptySet<String>(),
            gameObjects : ArrayList<GameObject> = ArrayList(),
            mousePosition : Vec2 = Vec2(),
            gameObjectsToAdd : ArrayList<GameObject> = ArrayList()
    ) : Boolean {
        return true;
    }

    fun update() {
        modelMatrix.set()
                .scale(scale)
                .rotate(roll)
                .translate(position)
    }

    fun hit(other : GameObject): Boolean
    {
        if (other is Background)
            return false

        val r1 = if (scale.x > scale.y) scale.x else scale.y
        val r2 = if (other.scale.x > other.scale.y) other.scale.x else other.scale.y
        val d = (position - other.position).length()

        return r1 + r2 > d;
    }
}