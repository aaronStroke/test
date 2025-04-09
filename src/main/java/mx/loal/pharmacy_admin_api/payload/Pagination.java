package mx.loal.pharmacy_admin_api.payload;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ApiResponse(description = "Objeto que contiene la información de una lista de elementos y su paginación")
public class Pagination<T> {

    private List<T> content;

    private Boolean empty;
    private Boolean first;
    private Boolean last;

    private Integer number;
    private Integer numberOfElements;
    private Integer size;
    private Integer totalPages;

    private Long totalElements;

}
