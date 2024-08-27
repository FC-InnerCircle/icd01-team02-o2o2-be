package org.example.o2o.domain.menu;

import java.util.ArrayList;
import java.util.List;

import org.example.o2o.domain.AbstractEntity;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.store.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class StoreMenu extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StoreMenuOptionGroup> menuOptionGroups = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "image_file_group_id")
	private FileGroup imageFileGroup;

	private String name;
	private String description;
	private Integer price;
	private Integer ordering;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "store_id")
	private Store store;

	@Enumerated(EnumType.STRING)
	private StoreMenuStatus status;

	public String getThumbImageUrl() {
		if (imageFileGroup == null || imageFileGroup.getDetails().isEmpty()) {
			return "";
		}
		return imageFileGroup.getDetails().get(0).getPath();
	}
}
