package com.example.dilla.exception;

import com.example.dilla.response.WebResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    //   custom exception untuk not found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<WebResponse<String>> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                WebResponse.<String>builder()
                        .status("Fail")
                        .message(e.getMessage())
                        .data(null)
                        .build()
        );
    }

    //    validasi error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<List<String>>> handleValidation(MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                WebResponse.<List<String>>builder()
                        .status("Bad Request")
                        .message("Input Tidak Valid")
                        .data(errorMessages)
                        .build()
        );
    }

    // method salah
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<WebResponse<String>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                WebResponse.<String>builder()
                        .status("fail")
                        .message("Method tidak diizinkan")
                        .data(null)
                        .build()
        );
    }

    // optimis locking

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<WebResponse<String>> handleOptimisticLocking(ObjectOptimisticLockingFailureException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                WebResponse.<String>builder()
                        .status("Fail")
                        .message("Data sudah diubah oleh user lain, silakan refresh")
                        .data(null)
                        .build()
        );
    }

//    @ExceptionHandler(RuntimeException.class )
//    public WebResponse<String> handleResponseStatusException(RuntimeException e) {
//        return WebResponse.<String>builder()
//                .status("fail")
//                .message("SKU CODE TIDAK BOLEH SAMA")
//                .data(null)
//                .build();
//    }

    // untuk menghandle error yg tidak tertangkap
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<WebResponse<String>> handleGeneral(Exception e) {
//        e.printStackTrace();
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                WebResponse.<String>builder()
//                        .status("Error")
//                        .message(e.getMessage())
//                        .data(null)
//                        .build()
//        );
    }




