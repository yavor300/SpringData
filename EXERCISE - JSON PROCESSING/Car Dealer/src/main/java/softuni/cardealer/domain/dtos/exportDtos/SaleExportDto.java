package softuni.cardealer.domain.dtos.exportDtos;

import com.google.gson.annotations.Expose;

public class SaleExportDto {
    @Expose
    private String name;

    public SaleExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
