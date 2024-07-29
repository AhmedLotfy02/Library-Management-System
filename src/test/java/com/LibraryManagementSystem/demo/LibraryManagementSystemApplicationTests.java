package com.LibraryManagementSystem.demo;

import com.LibraryManagementSystem.demo.controller.BookController;
import com.LibraryManagementSystem.demo.controller.BorrowingController;
import com.LibraryManagementSystem.demo.controller.JwtAuthenticationController;
import com.LibraryManagementSystem.demo.controller.PatronController;
import com.LibraryManagementSystem.demo.service.BookService;
import com.LibraryManagementSystem.demo.service.BorrowingRecordService;
import com.LibraryManagementSystem.demo.service.PatronService;
import com.LibraryManagementSystem.demo.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibraryManagementSystemApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}
	@Test
	void testBookControllerPresence() {
		// Verify that the BookController bean is loaded
		BookController bookController = applicationContext.getBean(BookController.class);
		assertThat(bookController).isNotNull();
	}

	@Test
	void testPatronControllerPresence() {
		// Verify that the PatronController bean is loaded
		PatronController patronController = applicationContext.getBean(PatronController.class);
		assertThat(patronController).isNotNull();
	}
	@Test
	void testBorrowingControllerPresence() {
		// Verify that the BorrowingController bean is loaded
		BorrowingController borrowingController = applicationContext.getBean(BorrowingController.class);
		assertThat(borrowingController).isNotNull();
	}
	@Test
	void testJwtAuthenticationControllerPresence() {
		// Verify that the JwtAuthenticationController bean is loaded
		JwtAuthenticationController jwtAuthenticationController = applicationContext.getBean(JwtAuthenticationController.class);
		assertThat(jwtAuthenticationController).isNotNull();
	}
	@Test
	void testBookServicePresence() {
		// Verify that the BookService bean is loaded
		BookService bookService = applicationContext.getBean(BookService.class);
		assertThat(bookService).isNotNull();
	}

	@Test
	void testPatronServicePresence() {
		// Verify that the PatronService bean is loaded
		PatronService patronService = applicationContext.getBean(PatronService.class);
		assertThat(patronService).isNotNull();
	}
	@Test
	void testBorrowingRecordServicePresence() {
		// Verify that the BorrowingRecordService bean is loaded
		BorrowingRecordService borrowingRecordService = applicationContext.getBean(BorrowingRecordService.class);
		assertThat(borrowingRecordService).isNotNull();
	}
	@Test
	void testUserDetailsServicePresence() {
		// Verify that the UserDetailsServiceImpl bean is loaded
		UserDetailsServiceImpl userDetailsService = applicationContext.getBean(UserDetailsServiceImpl.class);
		assertThat(userDetailsService).isNotNull();
	}
}
