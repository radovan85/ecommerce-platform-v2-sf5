package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.OrderItemDto;
import com.radovan.spring.entity.OrderItemEntity;
import com.radovan.spring.repository.OrderItemRepository;
import com.radovan.spring.service.OrderItemService;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository itemRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public List<OrderItemDto> listAllByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		List<OrderItemDto> returnValue = new ArrayList<OrderItemDto>();
		Optional<List<OrderItemEntity>> allItemsOpt = Optional.ofNullable(itemRepository.findAllByOrderId(orderId));
		if (!allItemsOpt.isEmpty()) {
			allItemsOpt.get().forEach((itemEntity) -> {
				OrderItemDto itemDto = tempConverter.orderItemEntityToDto(itemEntity);
				returnValue.add(itemDto);
			});
		}
		return returnValue;
	}

}
