package com.example.demo.service;

import com.example.demo.module.Candidate;
import com.example.demo.module.Disciplines;
import com.example.demo.repository.CandidateRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

        public String upload(MultipartFile file, Integer numberOfSheet) throws IOException {
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());

            if (numberOfSheet == null || numberOfSheet < 0) {
                numberOfSheet = workbook.getNumberOfSheets();
            }

            DataFormatter dataFormatter = new DataFormatter();

            ArrayList<String> emailList = new ArrayList<>();


                Sheet sheet = workbook.getSheetAt(0);
                Sheet sheet1 = workbook.getSheetAt(1);
                Sheet sheet2 = workbook.getSheetAt(2);

                System.out.println("=> " + sheet.getSheetName());

                int columnIndexToReadAndSkip = 21;

                int lastRowNum = sheet.getLastRowNum();

                for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        Cell cellToReadAndSkip = row.getCell(columnIndexToReadAndSkip);

                        if (cellToReadAndSkip != null) {
                            String cellValue = dataFormatter.formatCellValue(cellToReadAndSkip);

                            if (emailList.contains(cellValue)) {
                                System.out.println("Duplicate email: " + cellValue + ". Skipping row.");
                                continue;
                            } else {
                                emailList.add(cellValue);
                            }

                            Candidate candidate = new Candidate();
                            for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {

                                Cell cell = row.getCell(colIndex);
                                if (cell != null) {
                                    String cellValueToPrint = dataFormatter.formatCellValue(cell);
                                    switch (colIndex){
                                        case 0:
                                            candidate.setCandidateID(cellValueToPrint);
                                        case 1:
                                            candidate.setRefNo(cellValueToPrint);
                                        case 2:
                                            candidate.setDateReg(cellValueToPrint);
                                        case 3:
                                            candidate.setFirstName(cellValueToPrint);
                                        case 4:
                                            candidate.setLastName(cellValueToPrint);
                                        case 5:
                                            candidate.setDateOfBirth(cellValueToPrint);
                                        case 6:
                                            candidate.setNic(cellValueToPrint);
                                        case 7:
                                            candidate.setNationality(cellValueToPrint);
                                        case 21:
                                            candidate.setEmail(cellValueToPrint);
                                        case 20:
                                            candidate.setContactNumber(cellValueToPrint);
                                        default:
                                            break;
                                    }

                                if( rowIndex != 0 && colIndex == 0){


                                    int sheet1LastRow = sheet1.getLastRowNum();

                                    for(int rowind = 0; rowind <= sheet1LastRow; rowind++){
//                                        System.out.println(cellValueToPrint);
                                        Row row1 = sheet1.getRow(rowind);
                                        Cell cell1 = row1.getCell(1);
                                        String cell1Value = dataFormatter.formatCellValue(cell1);
//                                        System.out.println("val 2" + cell1Value);

                                        if(Objects.equals(cellValueToPrint, cell1Value)){
                                            System.out.println("same row");
                                            Cell discCell = row1.getCell(2);
                                            String disciplineId = dataFormatter.formatCellValue(discCell);
                                            int sheet2LastRow = sheet2.getLastRowNum();

                                            for(int rowin = 0; rowin <= sheet2LastRow; rowin++){
                                                Row row2 = sheet2.getRow(rowin);

                                                Cell cell2 = row2.getCell(0);
                                                String cell2Value = dataFormatter.formatCellValue(cell2);

                                                if(Objects.equals(disciplineId, cell2Value)){

                                                    List<Disciplines> disciplines = new ArrayList<>();
                                                    Disciplines discipline = new Disciplines();
                                                    for(int col=0; col<row2.getLastCellNum(); col++){
                                                        Cell disCell = row2.getCell(col);
                                                        String cellval = dataFormatter.formatCellValue(disCell);
                                                        System.out.println("value" + cellval);

                                                        switch (col){
                                                            case 0:
                                                                discipline.setDisciplineId(cellval);
                                                                break;
                                                            case 1:
                                                                discipline.setIndustryId(cellval);
                                                                break;
                                                            case 2:
                                                                discipline.setDescription(cellval);
                                                                break;
                                                            case 3:
                                                                discipline.setColour(cellval);
                                                                break;
                                                            default:
                                                                break;
                                                        }

                                                    }
                                                    disciplines.add(discipline);
//                                                return disciplines;
                                                    candidate.setDisciplines(disciplines);
//                                                    candidateRepository.save(candidate);
                                                }

                                            }
                                            candidateRepository.save(candidate);
                                        }
                                    }


                                }

                                }
                            }
//                            candidateRepository.save(candidate);
                        } else {
                            System.out.println("Skipping row due to null cell");
                            continue;
                        }
                    }
//                    System.out.println();
                }


            return "OK";
        }





}
