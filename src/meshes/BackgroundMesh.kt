package meshes

import Mesh
import Program
import Texture2D
import TexturedMaterial
import WebGL2RenderingContext
import vision.gears.webglmath.Geometry

class BackgroundMesh(
        gl : WebGL2RenderingContext,
        program : Program,
        geometry: Geometry,
        material: TexturedMaterial = TexturedMaterial(
                program,
                Texture2D(gl, "media/SpaceShooterRedux/Backgrounds/darkPurple.png")
        )
) : Mesh(material, geometry){

    init{
        addComponentsAndGatherUniforms(material, geometry)
    }
}