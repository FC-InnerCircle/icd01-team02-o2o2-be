package org.example.o2o.domain.store;

import java.util.ArrayList;
import java.util.List;

import org.example.o2o.domain.AbstractEntity;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.menu.StoreMenu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Store extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String contactNumber;
	private String zipCode;
	private String address;
	private String addressDetail;
	private Double latitude;
	private Double longitude;
	private String openTime;
	private String closeTime;
	private String category;
	private String deliveryArea;
	private Integer minimumOrderAmount;

	@Enumerated(EnumType.STRING)
	private StoreStatus status;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "thumbnail_file_group_id")
	private FileGroup thumbnailFileGroup;

	@Builder.Default
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StoreBusinessDay> businessDays = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
	private List<StoreMenu> menus = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "store")
	private StoreRateScore storeRateScore;

	public void registerThumbnailFile(FileGroup fileGroup) {
		this.thumbnailFileGroup = fileGroup;
	}

	public void registerBusinessDays(List<StoreBusinessDay> businessDays) {
		for (StoreBusinessDay businessDay : businessDays) {
			this.businessDays.add(businessDay);
			businessDay.setStore(this);
		}
	}

	public void delete() {
		this.status = StoreStatus.DELETED;
	}
}
