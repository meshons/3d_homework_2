package meshes.meteor

import Mesh
import Program
import Texture2D
import TexturedMaterial
import WebGL2RenderingContext
import vision.gears.webglmath.Geometry

class MeteorBigMesh7(
        gl : WebGL2RenderingContext,
        program : Program,
        geometry: Geometry,
        material: TexturedMaterial = TexturedMaterial(
                program,
                Texture2D(gl, "media/SpaceShooterRedux/PNG/Meteors/meteorGrey_big3.png")
        )
) : Mesh(material, geometry){

    init{
        addComponentsAndGatherUniforms(material, geometry)
    }
}