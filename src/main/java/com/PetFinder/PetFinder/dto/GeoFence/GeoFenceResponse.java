package com.PetFinder.PetFinder.dto.GeoFence;

import com.PetFinder.PetFinder.dto.CoordinateDto;

import java.util.List;

public class GeoFenceResponse {
    private Long id;
    private String name;
    private Long petId;
    private List<CoordinateDto> points;

    public GeoFenceResponse() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getPetId() {
        return this.petId;
    }

    public List<CoordinateDto> getPoints() {
        return this.points;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public void setPoints(List<CoordinateDto> points) {
        this.points = points;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GeoFenceResponse)) return false;
        final GeoFenceResponse other = (GeoFenceResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$petId = this.getPetId();
        final Object other$petId = other.getPetId();
        if (this$petId == null ? other$petId != null : !this$petId.equals(other$petId)) return false;
        final Object this$points = this.getPoints();
        final Object other$points = other.getPoints();
        if (this$points == null ? other$points != null : !this$points.equals(other$points)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GeoFenceResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $petId = this.getPetId();
        result = result * PRIME + ($petId == null ? 43 : $petId.hashCode());
        final Object $points = this.getPoints();
        result = result * PRIME + ($points == null ? 43 : $points.hashCode());
        return result;
    }

    public String toString() {
        return "GeoFenceResponse(id=" + this.getId() + ", name=" + this.getName() + ", petId=" + this.getPetId() + ", points=" + this.getPoints() + ")";
    }
}
