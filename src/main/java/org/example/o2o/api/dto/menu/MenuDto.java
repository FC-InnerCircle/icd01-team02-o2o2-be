package org.example.o2o.api.dto.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.data.domain.Page;

import lombok.Builder;

public class MenuDto {

	@Builder
	public record StoreMenusResponse(
		List<Menu> menus, Integer page, Integer size, Long totalLength
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
			Integer id,
			StoreMenuStatus status,
			String name,
			String desc,
			Integer price,
			String thumbImageUrl
		) {
			public static Menu of(StoreMenu menu) {
				return Menu.builder()
					.id(menu.getId())
					.status(menu.getStatus())
					.name(menu.getName())
					.desc(menu.getDescription())
					.price(menu.getPrice())
					.thumbImageUrl(menu.getImageFileGroup().getDetails().get(0).getPath())
					.build();
			}
		}

		private record Image(
			Integer seq,
			String imageUrl
		) {
			public static Image of(FileDetail fileDetail) {
				return new Image(fileDetail.getOrdering(), fileDetail.getPath());
			}
		}
	}
}
