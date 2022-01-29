package ua.com.alevel.elastic.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sneaker_index")
public class SneakerIndex {

    @Id
    private String id;

    @Field(name = "sneakerName", type = FieldType.Text)
    private String sneakerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSneakerName() {
        return sneakerName;
    }

    public void setSneakerName(String sneakerName) {
        this.sneakerName = sneakerName;
    }
}
