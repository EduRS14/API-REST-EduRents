package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Producto_Publico", description = "API de Gestion de Productos para un usuario registrado/no registrado")
@RestController
@RequestMapping("/public/products")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicProductController {

    private final PublicProductService publicProductService;

    @Operation(
            summary = "Buscar productos por nombre",
            description = "Permite a un usuario buscar productos por su nombre. " +
                    "Se devuelve una lista de objetos ShowProductDTO que coinciden con el nombre proporcionado.",
            tags = {"productos", "nombre", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @GetMapping("/search/{nombre}")
    public ResponseEntity<List<ShowProductDTO>> buscarProductoPorNombre(@PathVariable String nombre) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductoPorNombre(nombre);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    //HU 01
    @Operation(
            summary = "Obtener un producto por su ID",
            description = "Permite a un usuario obtener los detalles de un producto específico por su ID. " +
                    "Se devuelve un objeto ShowProductDTO con los detalles del producto, como su nombre, descripción, " +
                    "precio y estado de disponibilidad.",
            tags = {"productos", "id", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShowProductDTO> obtenerProductoPorId(@PathVariable Integer id){
        ShowProductDTO producto = publicProductService.obtenerProductoPorId(id);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    // HU 03
    @Operation(
            summary = "Obtener todos los productos",
            description = "Permite a un usuario obtener una lista de todos los productos disponibles. " +
                    "Se devuelve una lista de objetos ShowProductDTO, cada uno representando un producto con sus detalles.",
            tags = {"productos", "varios", "todos", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @GetMapping
    public ResponseEntity<List<ShowProductDTO>> obtenerProductos(){
        List<ShowProductDTO> productos = publicProductService.obtenerTodosLosProductos();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    //HU 04
    @Operation(
            summary = "Obtener productos por ID de carrera",
            description = "Devuelve una lista de productos asociados a una carrera especifica, identificada por su ID.",
            tags = {"productos", "carrera", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrera no encontrada o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarrera(@PathVariable Integer idCareer) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarrera(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por ID de curso",
            description = "Devuelve una lista de productos asociados a un curso especifico, identificado por su ID.",
            tags = {"productos", "curso", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Curso no encontrado o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/course/{idCourse}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCurso(@PathVariable Integer idCourse) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCurso(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por curso y carrera",
            description = "Filtra los productos segun el curso y la carrera, ambos identificados por su ID.",
            tags = {"productos", "curso", "carrera", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Curso o carrera no encontrada, o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{carreraId}/course/{cursoId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCursoYCarrera(@PathVariable Integer carreraId, @PathVariable Integer cursoId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCursoYCarrera(carreraId, cursoId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por ID de categoria",
            description = "Devuelve una lista de productos que pertenecen a una categoria especifica, identificada por su ID.",
            tags = {"productos", "categoria", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria no encontrada o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCategoria(categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por carrera, curso y categoria",
            description = "Filtra los productos combinando los filtros de carrera, curso y categoria. Todos deben ser identificados por su ID.",
            tags = {"productos", "carrera", "curso", "categoria", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron productos con los filtros especificados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("career/{idCareer}/course/{idCourse}/category/{idCategory}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarreraCursoCategoria(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarreraCursoYCategoria(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05

    @Operation(
            summary = "Obtener productos por carrera ordenados por vistas",
            description = "Filtra los productos por ID de carrera y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"productos", "carrera", "vistas", "varios", "todos", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerOrderByView(@PathVariable Integer idCareer){
        publicProductService.obtenerProductosPorCarreraOrdenarPorVistas(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por curso ordenados por vistas",
            description = "Filtra los productos por ID de curso y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"productos", "curso", "vistas", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/course/{idCourse}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCourseOrderByView(@PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCursoOrdenarPorVistas(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por carrera y curso ordenados por vistas",
            description = "Filtra los productos por carrera y curso, y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"productos", "carrera", "curso", "vistas", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/course/{idCourse}/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCarreraPorCursoOrdenarPorVistas(idCareer, idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por categoria ordenados por vistas",
            description = "Filtra los productos por ID de categoria y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"productos", "categorias", "vistas", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCategoryOrderByView(@PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCategoriaOrdernarPorVistas(idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por carrera, curso y categoria ordenados por vistas",
            description = "Filtra los productos combinando carrera, curso y categoria, y los ordena por vistas de mayor a menor.",
            tags = {"productos", "carrera", "curso", "categoria", "vistas", "varios", "filtro", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{idCareer}/course/{idCourse}/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseCategoryOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    // HU 06
    @Operation(
            summary = "Obtener productos trendy (mas vistos)",
            description = "Retorna un top de productos basado en la cantidad de vistas. El top es un 10% o 5% del total de productos, segun la cantidad disponible.",
            tags = {"productos", "tendencia", "vistas", "varios", "top", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos trendy ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos disponibles"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/products/trendy")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTrendy(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorVistas();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos con mas propuestas de intercambio",
            description = "Retorna un top de productos basado en la cantidad de propuestas de intercambio recibidas. Se calcula segun la cantidad de registros en la tabla exchangeOffer. El top es un 10% o 5% del total de productos.",
            tags = {"productos", "intercambio", "varios", "top", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por cantidad de intercambios",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos con propuestas de intercambio"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/products/top-exchanges")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTopExchange(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorCantidadDeIntercambios();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos recientes",
            description = "Retorna productos ordenados por fecha de creacion descendente. Puede considerarse solo un orden o un top limitado segun definicion futura.",
            tags = {"productos", "recientes", "fecha", "varios", "top", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por fecha de creacion",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos recientes disponibles"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/products/recents")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosRecents(){
        List<ShowProductDTO> productos = publicProductService.obtener10ProductosRecientes();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU10
    // HU10 - Endpoint 01: Obtener cantidad disponible
    @Operation(summary = "Consultar stock disponible", description = "Devuelve la cantidad disponible (stock) de un producto por su ID.",
                tags = {"productos", "stock", "publico", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad disponible obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/stock")
    public StockDTO obtenerStock(@PathVariable Integer id) {
        return publicProductService.obtenerStockProductoPorId(id);
    }

    // HU10 - Endpoint 02: Obtener fecha de expiración
    @Operation(summary = "Consultar fecha de expiración del producto", description = "Devuelve la fecha de expiración de la oferta del producto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> obtenerFechaExpiracion(@PathVariable Integer id) {
        ShowProductDTO dto = publicProductService.obtenerFechaExpiracion(id);
        return ResponseEntity.ok(dto);
    }

    // HU10 - Endpoint 03: Obtener estado
    @Operation(summary = "Consultar estado del producto", description = "Devuelve el estado actual del producto (nuevo, usado, etc.).",
            tags = {"productos", "estado", "publico", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del producto obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/state")
    public ResponseEntity<ShowProductDTO> obtenerEstadoProducto(@PathVariable Integer id) {
        ShowProductDTO dto = publicProductService.obtenerEstado(id);
        return ResponseEntity.ok(dto);
    }

    // HU10 - Endpoint 04: Consultar si acepta intercambio
    @Operation(summary = "Consultar si el producto acepta intercambio", description = "Indica si el producto está disponible para intercambio.",
            tags = {"productos", "intercambio", "publico", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/exchange")
    public ResponseEntity<ShowProductDTO> obtenerSiProductoEsIntercambiable(@PathVariable Integer id) {
        ShowProductDTO dto = publicProductService.obtenerEstadoAceptaIntercambio(id);
        return ResponseEntity.ok(dto);
    }






}
