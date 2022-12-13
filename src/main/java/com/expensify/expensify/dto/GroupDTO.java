package com.expensify.expensify.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.expensify.expensify.utility.validator.CollectionMinSize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
	private Long id;
	@NotNull(message = "group name is required")
	private String grpName;
	private String grpType;
	@CollectionMinSize(min = 1, message = "minimum {min} group user are required")
	private List<String> grpUser;
}
