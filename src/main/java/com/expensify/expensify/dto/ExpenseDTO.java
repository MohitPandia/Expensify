package com.expensify.expensify.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.expensify.expensify.utility.validator.CollectionMinSize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

	@NotNull(message = "expense name is required")
	private String expName;
	@NotNull(message = "expense amount is requird")
	private double expAmt;
	@NotNull(message = "expense pay by user is requied")
	private Long expPaidBy;

	@CollectionMinSize(min = 1)
	private List<@Valid SplitDTO> usrSplitBtw;
	private Long expGrp;
	private ExpenseDataDTO expenseData;
	@NotNull(message = "expense type is required")
	private String expType;

}
