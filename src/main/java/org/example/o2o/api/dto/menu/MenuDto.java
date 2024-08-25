package org.example.o2o.api.dto.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuOption;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.data.domain.Page;

import lombok.Builder;

public class MenuDto {

	@Builder
	public record StoreMenusResponse(
		List<Menu> menus,
		int page,
		int size,
		long totalLength
	) {
		public static StoreMenusResponse of(Page<StoreMenu> menuPage) {
			return StoreMenusResponse.builder()
				.menus(menuPage.getContent()
					.stream()
					.map(Menu::of)
					.collect(Collectors.toList()))
				.size(menuPage.getSize())
				.page(menuPage.getNumber())
				.totalLength(menuPage.getTotalElements())
				.build();
		}

		@Builder
		private record Menu(
			int id,
			StoreMenuStatus status,
			String name,
			String desc,
			int price,
			String thumbImageUrl,
			int ordering
		) {
			public static Menu of(StoreMenu menu) {
				return Menu.builder()
					.id(menu.getId())
					.status(menu.getStatus())
					.name(menu.getName())
					.desc(menu.getDescription())
					.price(menu.getPrice())
					.thumbImageUrl(menu.getThumbImageUrl())
					.ordering(menu.getOrdering())
					.build();
			}
		}
	}

	@Builder
	public record StoreMenuDetailResponse(
		long menuId,
		StoreMenuStatus status,
		String name,
		String desc,
		int price,
		List<Image> images,
		List<MenuOptionGroup> optionGroups
	) {

		public static StoreMenuDetailResponse of(StoreMenu menu) {
			return StoreMenuDetailResponse.builder()
				.menuId(menu.getId())
				.status(menu.getStatus())
				.name(menu.getName())
				.desc(menu.getDescription())
				.price(menu.getPrice())
				.images(menu.getImageFileGroup()
					.getDetails()
					.stream()
					.map(Image::of)
					.collect(Collectors.toList()))
				.optionGroups(menu.getMenuOptionGroups()
					.stream()
					.map(MenuOptionGroup::of)
					.collect(Collectors.toList()))
				.build();
		}

		@Builder
		public record Image(long imageId, int ordering, String imageUrl) {

			public static Image of(FileDetail detail) {
				return new Image(detail.getId(), detail.getOrdering(), detail.getPath());
			}
		}

		@Builder
		public record MenuOptionGroup(
			long optionGroupId,
			int ordering,
			boolean isRequired,
			String title,
			List<Option> options
		) {

			public static MenuOptionGroup of(StoreMenuOptionGroup optionGroup) {
				return MenuOptionGroup.builder()
					.optionGroupId(optionGroup.getId())
					.ordering(optionGroup.getOrdering())
					.isRequired(optionGroup.getIsRequired())
					.title(optionGroup.getName())
					.options(optionGroup.getOptions()
						.stream()
						.map(Option::of)
						.collect(Collectors.toList()))
					.build();
			}

			@Builder
			public record Option(
				long optionId,
				int ordering,
				int price,
				String name,
				String desc
			) {

				public static Option of(StoreMenuOption option) {
					return Option.builder()
						.optionId(option.getId())
						.ordering(option.getOrdering())
						.price(option.getPrice())
						.name(option.getName())
						.desc(option.getDescription())
						.build();
				}
			}
		}
	}
}
