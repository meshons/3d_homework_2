import vision.gears.webglmath.Geometry
import vision.gears.webglmath.UniformProvider

open class Mesh(material : Material, geometry : Geometry)
    : UniformProvider("mesh")  {

    init{
        addComponentsAndGatherUniforms(material, geometry)
    }

    companion object {
        private val meshMap = HashMap<String, Mesh>()
        fun add(name : String, mesh : Mesh)
        {
            meshMap.put(name, mesh)
        }
        fun get(name : String): Mesh
        {
            return meshMap.getValue(name)
        }
    }
}