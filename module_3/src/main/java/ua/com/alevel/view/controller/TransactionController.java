package ua.com.alevel.view.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.CategoryFacade;
import ua.com.alevel.facade.TransactionFacade;
import ua.com.alevel.view.dto.category.CategoryResponseDto;
import ua.com.alevel.view.dto.transaction.TransactionRequestDto;
import ua.com.alevel.view.dto.transaction.TransactionResponseDto;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController extends BaseController {

    private final TransactionFacade transactionFacade;
    private final CategoryFacade categoryFacade;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    public TransactionController(TransactionFacade transactionFacade, CategoryFacade categoryFacade) {
        this.transactionFacade = transactionFacade;
        this.categoryFacade = categoryFacade;
    }

    @GetMapping
    public String all(Model model,
                      @RequestParam(required = false) Long accountId,
                      @RequestParam(required = false) Long categoryId,
                      @RequestParam(required = false) Long userId,
                      @RequestParam(required = false) boolean statement) {
        List<TransactionResponseDto> transactions;
        if (accountId != null) {
            transactions = transactionFacade.findAllByAccountId(accountId);
        } else if (categoryId != null) {
            transactions = transactionFacade.findAllByCategoryId(categoryId);
        } else if(userId != null) {
            transactions = transactionFacade.findAllByUserId(userId);
        } else {
            transactions = transactionFacade.findAll();
        }
        if(statement) {
            model.addAttribute("statement", true);
            transactionFacade.exportAccountStatement(accountId);
        }
        model.addAttribute("transactions", transactions);
        return "pages/transaction/transaction_all";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        TransactionResponseDto transaction = transactionFacade.findById(id);
        model.addAttribute("transaction", transaction);
        return "pages/transaction/transaction_details";
    }

    @GetMapping("/new")
    public String redirectToNewTransactionPage(@RequestParam Long accountId, Model model) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setAccountId(accountId);
        List<CategoryResponseDto> categories = categoryFacade.findAll();
        model.addAttribute("transaction", dto);
        model.addAttribute("categories", categories);
        return "pages/transaction/transaction_new";
    }

    @PostMapping("/new")
    public String createNewTransaction(@ModelAttribute("transaction") TransactionRequestDto dto) {
        try {
            Double.parseDouble(dto.getAmount());
        } catch (NumberFormatException e) {
            LOGGER_WARN.warn("Failed transaction attempt. Invalid amount.");
            throw new RuntimeException("Invalid amount. Note: it must be only two digits after DOT. Don't use comma.");
        }
        transactionFacade.create(dto);
        return "redirect:/transactions";
    }
}
