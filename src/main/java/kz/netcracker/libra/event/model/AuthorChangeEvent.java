package kz.netcracker.libra.event.model;

import kz.netcracker.libra.dto.AuthorDto;
import lombok.Data;

@Data
public class AuthorChangeEvent {
    private Payload payload;
    
    @Data
    public static class Payload {
        private AuthorDto before;
        private AuthorDto after;
        private Source source;
        private String op; // c=create, u=update, d=delete
        private Long ts_ms;
    }
    
//    @Data
//    public static class Before {
//        private Long id;
//        private String name;
//    }
//
//    @Data
//    public static class After {
//        private Long id;
//        private String name;
//    }
    
    @Data
    public static class Source {
        private String version;
        private String connector;
        private String name;
        private String db;
        private String schema;
        private String table;
    }
}