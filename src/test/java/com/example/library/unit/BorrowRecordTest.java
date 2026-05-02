package com.example.library.unit;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.example.library.model.Book;
import com.example.library.model.BorrowRecord;
import com.example.library.model.BorrowStatus;
import com.example.library.model.Genre;
import com.example.library.model.Member;
import com.example.library.model.MembershipType;

/**
 * UNIT TEST - Model Layer
 */
class BorrowRecordTest {

    private Book createSampleBook() {
        Book book = new Book("978-0-13-468599-1", "Clean Code", "Robert C. Martin", 3, Genre.TECHNOLOGY);
        book.setId(1L);
        return book;
    }

    private Member createSampleMember() {
        Member member = new Member("Alice", "alice@example.com", MembershipType.STANDARD);
        member.setId(1L);
        return member;
    }

    // =========================================================================
    // EXAMPLE: calculateFine() tests — filled in as reference
    // =========================================================================

    @Nested
    @DisplayName("calculateFine()")
    class CalculateFineTests {

        @Test
        @DisplayName("should return 0 when book is returned on time")
        void shouldReturnZeroFine_WhenReturnedOnTime() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            record.setReturnDate(record.getDueDate()); // returned exactly on due date

            assertEquals(0.0, record.calculateFine());
        }

        @Test
        @DisplayName("should return 0 when book is returned before due date")
        void shouldReturnZeroFine_WhenReturnedEarly() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            record.setReturnDate(record.getBorrowDate().plusDays(5)); // returned after 5 days

            assertEquals(0.0, record.calculateFine());
        }

        @Test
        @DisplayName("should calculate correct fine when returned 3 days late")
        void shouldCalculateCorrectFine_WhenReturnedLate() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            record.setReturnDate(record.getDueDate().plusDays(3)); // 3 days late

            double expectedFine = 3 * BorrowRecord.DAILY_FINE_RATE; // 3 * 1.50 = 4.50
            assertEquals(expectedFine, record.calculateFine());
        }

        @Test
        @DisplayName("should return 0 when book is not yet returned")
        void shouldReturnZeroFine_WhenNotYetReturned() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            // returnDate is null

            assertEquals(0.0, record.calculateFine());
        }
    }

    // =========================================================================
    // TODO: Students should write these tests
    // =========================================================================

    @Nested
    @DisplayName("isOverdue()")
    class IsOverdueTests {

        @Test
        @DisplayName("should return true when checked after due date and still borrowed")
        void shouldBeOverdue_WhenPastDueDateAndStillBorrowed() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            LocalDate afterDue = record.getDueDate().plusDays(1);
            assertTrue(record.isOverdue(afterDue));
        }

        @Test
        @DisplayName("should return false when checked before due date")
        void shouldNotBeOverdue_WhenBeforeDueDate() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            LocalDate beforeDue = record.getDueDate().minusDays(1);
            assertFalse(record.isOverdue(beforeDue));
        }

        @Test
@DisplayName("should return false when book is already returned (even if past due)")
void shouldNotBeOverdue_WhenAlreadyReturned() {
    
    BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());

    
    record.setStatus(BorrowStatus.RETURNED);
    record.setReturnDate(record.getDueDate().plusDays(5));

    
    LocalDate checkDate = record.getDueDate().plusDays(10);

    assertFalse(record.isOverdue(checkDate),
            "Returned books should not be considered overdue even if checked after the due date.");
}

        @Test
        @DisplayName("should return false on exactly the due date")
        void shouldNotBeOverdue_OnExactDueDate() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            LocalDate exactDueDate = record.getDueDate();
            
            assertFalse(record.isOverdue(exactDueDate));
        }
    }

    @Nested
    @DisplayName("Constructor / default values")
    class ConstructorTests {

        @Test
        @DisplayName("should set borrow date to today")
        void shouldSetBorrowDateToToday() {
            // TODO: Verify that new BorrowRecord sets borrowDate to LocalDate.now()
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            LocalDate today = LocalDate.now();
            assertEquals(today, record.getBorrowDate());
        }

        @Test
        @DisplayName("should set due date to 14 days from today")
        void shouldSetDueDateTo14DaysFromToday() {
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());
            LocalDate expectedDueDate = LocalDate.now().plusDays(14);
            assertEquals(expectedDueDate, record.getDueDate());
        }

        @Test
        @DisplayName("should set status to BORROWED")
        void shouldSetStatusToBorrowed() {
            // TODO: Verify default status is BORROWED
            //Arrange and Act
            BorrowRecord record = new BorrowRecord(createSampleBook(), createSampleMember());

            //Assert
            assertEquals(BorrowStatus.BORROWED, record.getStatus());
        }
    }
}
