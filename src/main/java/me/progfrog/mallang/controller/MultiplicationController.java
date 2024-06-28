package me.progfrog.mallang.controller;

import lombok.RequiredArgsConstructor;
import me.progfrog.mallang.service.MultiplicationService;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MultiplicationController {

    private final MultiplicationService multiplicationService;
}
