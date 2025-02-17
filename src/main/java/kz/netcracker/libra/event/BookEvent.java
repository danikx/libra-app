package kz.netcracker.libra.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEvent {

    private Long id;
    private String title;
    private String isbn;
    private String authorFullName;
}