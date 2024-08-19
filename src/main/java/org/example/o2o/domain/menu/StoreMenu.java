package org.example.o2o.domain.menu;

import java.util.List;

import org.example.o2o.domain.AbstractEntity;
import org.example.o2o.domain.file.FileGroup;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoreMenu extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "menu")
	private List<StoreMenuOptionGroup> menuOptionGroups;

	@OneToOne
	@JoinColumn(name = "image_file_group_id")
	private FileGroup imageFileGroupId;

	private String name;
	private String description;
	private Integer price;
	private String status;

}
