package org.example.o2o.domain.file;

import java.util.ArrayList;
import java.util.List;

import org.example.o2o.domain.AbstractEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class FileGroup extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private FileGroupType groupType;

	@Builder.Default
	@OneToMany(mappedBy = "fileGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FileDetail> details = new ArrayList<>();

	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private StoreMenu menu;

	public static FileGroup createFileGroup(FileGroupType groupType) {
		return FileGroup.builder()
			.groupType(groupType)
			.build();
	}

	public void addDetail(FileDetail detail) {
		details.add(detail);
		detail.setFileGroup(this);
	}
}
