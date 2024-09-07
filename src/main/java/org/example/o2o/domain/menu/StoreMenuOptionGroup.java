package org.example.o2o.domain.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.menu.MenuErrorCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class StoreMenuOptionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private StoreMenu menu;

	@Builder.Default
	@OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StoreMenuOption> options = new ArrayList<>();

	private String title;
	private Boolean isRequired;
	private Integer ordering;

	public void addMenuOption(StoreMenuOption menuOption) {
		options.add(menuOption);
		menuOption.setOptionGroup(this);
	}

	public void updateBy(StoreMenuOptionGroup afterOptionGroup) {
		if (!Objects.isNull(afterOptionGroup.title)) {
			this.title = afterOptionGroup.title;
		}
		if (!Objects.isNull(afterOptionGroup.ordering)) {
			this.ordering = afterOptionGroup.ordering;
		}
		if (!Objects.isNull(afterOptionGroup.isRequired)) {
			this.isRequired = afterOptionGroup.isRequired;
		}
		if (Objects.isNull(afterOptionGroup.options) || afterOptionGroup.options.isEmpty()) {
			throw new ApiException(MenuErrorCode.REQUIRED_MENU_OPTION);
		}
		this.options.clear();
		afterOptionGroup.options
			.forEach(option -> {
				this.options.add(option);
				option.setOptionGroup(this);
			});
	}
}
