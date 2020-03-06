import vision.gears.webglmath.Geometry
import vision.gears.webglmath.UniformProvider

open class Mesh(material : Material, geometry : Geometry)
    : UniformProvider("mesh")  {

    init{
        addComponentsAndGatherUniforms(material, geometry)
    }
}