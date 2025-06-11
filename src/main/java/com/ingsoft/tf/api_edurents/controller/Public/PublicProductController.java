package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "HU10 - Consultar disponibilidad y detalles confiables de un producto",
        description = "Permite consultar el stock, la vigencia, el estado y si acepta intercambio para ayudar a decidir si se desea hacer una transacción."
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/products")
public class PublicProductController {

    private final PublicProductService publicProductService;

    //HU 01
    @GetMapping("/{id}")
    public ResponseEntity<ShowProductDTO> obtenerProductoPorId(@PathVariable Integer id){
        ShowProductDTO producto = publicProductService.obtenerProductoPorId(id);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    // HU 03
    @GetMapping
    public ResponseEntity<List<ShowProductDTO>> obtenerProductos(){
        List<ShowProductDTO> productos = publicProductService.obtenerTodosLosProductos();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    //HU 04

    @GetMapping("/career/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarrera(@PathVariable Integer idCareer) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarrera(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/course/{idCourse}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCurso(@PathVariable Integer idCourse) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCurso(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/career/{carreraId}/course/{cursoId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCursoYCarrera(@PathVariable Integer carreraId, @PathVariable Integer cursoId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCursoYCarrera(carreraId, cursoId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCategoria(categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("career/{idCareer}/course/{idCourse}/category/{idCategory}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarreraCursoCategoria(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarreraCursoYCategoria(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05

    @GetMapping("/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerOrderByView(@PathVariable Integer idCareer){
        publicProductService.obtenerProductosPorCarreraOrdenarPorVistas(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/course/{idCourse}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCourseOrderByView(@PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCursoOrdenarPorVistas(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/course/{idCourse}/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCarreraPorCursoOrdenarPorVistas(idCareer, idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCategoryOrderByView(@PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCategoriaOrdernarPorVistas(idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/career/{idCareer}/course/{idCourse}/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseCategoryOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    // HU 06

    @GetMapping("/products/trendy")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTrendy(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorVistas();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/products/top-exchanges")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTopExchange(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorCantidadDeIntercambios();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/products/recents")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosRecents(){
        List<ShowProductDTO> productos = publicProductService.obtener10ProductosRecientes();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }
    
    // HU10
    // HU10 - Endpoint 01: Obtener cantidad disponible
    @Operation(summary = "Consultar stock disponible", description = "Devuelve la cantidad disponible (stock) de un producto por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad disponible obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}/stock")
    public StockDTO obtenerStock(@PathVariable Integer id) {
        return publicProductService.obtenerStockProductoPorId(id);
    }
    /*
    // HU10 - Endpoint 01 (parte 2): Actualizar stock
    @Operation(summary = "Actualizar stock disponible", description = "Permite actualizar manualmente la cantidad disponible del producto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad negativa no permitida"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/update-stock")
    public ShowProductDTO actualizarCantidadDisponible(@PathVariable Integer id, @RequestBody Integer cantidad) {
        return publicProductService.actualizarCantidadDisponible(id, cantidad);
    }
    */
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
    /*
    @Operation(summary = "Actualizar fecha de expiración del producto", description = "Permite modificar la fecha de expiración de la oferta del producto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> actualizarFechaExpiracion(
            @PathVariable Integer id,
            @RequestBody ProductDTO request) {
        ShowProductDTO updatedProduct = publicProductService.actualizarFechaExpiracion(id, request.getFecha_expiracion());
        return ResponseEntity.ok(updatedProduct);
    }
    */
    // HU10 - Endpoint 03: Obtener estado
    @Operation(summary = "Consultar estado del producto", description = "Devuelve el estado actual del producto (nuevo, usado, etc.).")
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
    @Operation(summary = "Consultar si el producto acepta intercambio", description = "Indica si el producto está disponible para intercambio.")
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
