package softuni.cardealer.domain.dtos.exportDtos;

import com.google.gson.annotations.Expose;

public class LocalSupplierExportDto {
    @Expose
    private long id;
    @Expose
    private String Name;
    @Expose
    private int partsCount;

    public LocalSupplierExportDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
