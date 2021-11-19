package com.github.yt.web.test.example.controller;

import com.github.yt.web.result.PackageResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PackageResponseBody(false)
@RequestMapping("packageClassExtendTrue")
public class PackageClassExtendTrueController extends PackageClassTrueController {

}
