package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.ResenaRequestDTO;
import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Usuario_Reseñas", description = "API de gestion de reseñas como usuario ya registrado")
@RestController
@RequestMapping("/user/auth/reviews")
@PreAuthorize("hasAnyRole('USER', 'SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class UserAuthResenaController {

    private final UserAuthResenaService userAuthResenaService;

    @Operation(
            summary = "Obtener reseñas por la ID de un vendedor y que no sean del usuario autenticado",
            description = "Permite obtener las reseñas de un vendedor, excluyendo las del usuario autenticado. " +
                    "Requiere que el usuario esté autenticado y tenga el rol de 'USER', 'SELLER' o 'ADMIN'.",
            tags = {"usuario", "reseñas", "autenticado", "vendedor", "otros"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResenaResponseDTO.class))
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
    @GetMapping("/seller/{idVendedor}/reviews/others/{idUsuario}")
    public ResponseEntity<List<ResenaResponseDTO>> obtenerResenasPorVendedorYNoMismoUsuario(
            @PathVariable Integer idVendedor, @PathVariable Integer idUsuario) {
        List<ResenaResponseDTO> resenas = userAuthResenaService.obtenerResenasPorVendedorYNoMismoUsuario(idVendedor, idUsuario);
        return new ResponseEntity<List<ResenaResponseDTO>>(resenas, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener reseña por ID de vendedor y usuario",
            description = "Permite obtener una reseña específica de un vendedor por el ID del usuario autenticado. " +
                    "Requiere que el usuario esté autenticado y tenga el rol de 'USER', 'SELLER' o 'ADMIN'.",
            tags = {"usuario", "reseñas", "autenticado", "vendedor"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResenaResponseDTO.class)
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
    @GetMapping("/seller/{idVendedor}/reviews/user/{idUsuario}")
    public ResponseEntity<ResenaResponseDTO> obtenerResenaPorIdVendedorYIDUsuario(
            @PathVariable Integer idVendedor, @PathVariable Integer idUsuario) {
        ResenaResponseDTO resena = userAuthResenaService.obtenerResenaPorIdVendedorYIDUsuario(idVendedor, idUsuario);
        return new ResponseEntity<ResenaResponseDTO>(resena, HttpStatus.OK);
    }

    @Operation(
            summary = "Verificar si una reseña existe por ID de vendedor y usuario",
            description = "Permite verificar si una reseña ya existe para un vendedor y un usuario específicos. " +
                    "Requiere que el usuario esté autenticado y tenga el rol de 'USER', 'SELLER' o 'ADMIN'.",
            tags = {"usuario", "reseñas", "autenticado", "vendedor", "existente"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "boolean")
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
    @GetMapping("/seller/{idVendedor}/reviews/user/{idUsuario}/exists")
    public ResponseEntity<Boolean> resenaExistentePorIdVendedorYIDUsuario(
            @PathVariable Integer idVendedor, @PathVariable Integer idUsuario) {
        Boolean existe = userAuthResenaService.resenaExistentePorIdVendedorYIDUsuario(idVendedor, idUsuario);
        return new ResponseEntity<Boolean>(existe, HttpStatus.OK);
    }

    @Operation(
            summary = "Crear una nueva reseña",
            description = "Permite a un usuario autenticado crear una nueva reseña para un vendedor. " +
                    "Requiere que el usuario esté autenticado y tenga el rol de 'USER', 'SELLER' o 'ADMIN'.",
            tags = {"usuario", "reseñas", "autenticado", "crear"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResenaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @PostMapping()
    public ResponseEntity<ResenaResponseDTO> crearResena(@RequestBody ResenaRequestDTO request) {
        ResenaResponseDTO nuevaResena = userAuthResenaService.crearResena(request);
        return new ResponseEntity<ResenaResponseDTO>(nuevaResena, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar una reseña existente",
            description = "Permite a un usuario autenticado actualizar una reseña existente. " +
                    "Requiere que el usuario esté autenticado y tenga el rol de 'USER', 'SELLER' o 'ADMIN'.",
            tags = {"usuario", "reseñas", "autenticado", "actualizar"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResenaResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = { @Content(schema = @Schema())}
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
    @PutMapping("/{idResena}")
    public ResponseEntity<ResenaResponseDTO> actualizarResena(
            @PathVariable Integer idResena, @RequestBody ResenaRequestDTO request) {
        ResenaResponseDTO resenaActualizada = userAuthResenaService.actualizarResena(idResena, request);
        return new ResponseEntity<ResenaResponseDTO>(resenaActualizada, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar una reseña existente",
            description = "Permite a un usuario autenticado eliminar una reseña existente. " +
                    "Requiere que el usuario esté autenticado y tenga el rol de 'USER', 'SELLER' o 'ADMIN'.",
            tags = {"usuario", "reseñas", "autenticado", "eliminar"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    content = @Content()
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idResena}")
    public ResponseEntity<ResenaResponseDTO> eliminarResena(@PathVariable Integer idResena) {
        userAuthResenaService.eliminarResena(idResena);
        return new ResponseEntity<ResenaResponseDTO>(HttpStatus.NO_CONTENT);
    }

}
