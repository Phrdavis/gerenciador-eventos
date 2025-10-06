package com.gevents.gerenciador_eventos.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@MappedSuperclass
@SQLDelete(sql = "UPDATE #{#entityName} SET D_E_L_E_T_ = '*' WHERE id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = String.class))
@Filter(name = "deletedFilter", condition = "D_E_L_E_T_ = :isDeleted")
public abstract class BaseEntity {

    @Column(name = "D_E_L_E_T_", length = 1, nullable = false)
    private String deleted = " ";

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

}
