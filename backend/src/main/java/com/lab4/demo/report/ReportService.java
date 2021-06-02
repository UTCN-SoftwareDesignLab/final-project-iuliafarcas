package com.lab4.demo.report;

import java.io.ByteArrayOutputStream;

public interface ReportService {
    ByteArrayOutputStream export(String date);

    ReportType getType();
}
