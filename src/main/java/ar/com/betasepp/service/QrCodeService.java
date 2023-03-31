package ar.com.betasepp.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import ar.com.betasepp.dto.QrCodeGenerationRequestDto;
import lombok.var;

@Service
public class QrCodeService {

	public ResponseEntity<?> read(final MultipartFile file) throws IOException, NotFoundException {
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result = new MultiFormatReader().decode(binaryBitmap);
		return ResponseEntity.ok(new ObjectMapper().readValue(result.getText(), QrCodeGenerationRequestDto.class));
	}

	public void generateData(final QrCodeGenerationRequestDto qrCodeGenerationRequestDto,
			final HttpServletResponse httpServletResponse) throws IOException, WriterException {
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=data-qr.png");

		final var outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(new ObjectMapper().writeValueAsString(qrCodeGenerationRequestDto),
				BarcodeFormat.QR_CODE, 350, 350);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
		outputStream.flush();
	}

	public void qrCode(final String data, final HttpServletResponse httpServletResponse)
			throws IOException, WriterException {
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=data-qrCode.png");

		final var outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(new ObjectMapper().writeValueAsString(data), BarcodeFormat.QR_CODE, 350,
				350);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
		outputStream.flush();
	}

	public void dataMatrix(final String data, final HttpServletResponse httpServletResponse)
			throws IOException, WriterException {
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=data-dataMatrix.png");

		final var outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		DataMatrixWriter writer = new DataMatrixWriter();
		BitMatrix bitMatrix = writer.encode(new ObjectMapper().writeValueAsString(data), BarcodeFormat.DATA_MATRIX, 350,
				350);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
		outputStream.flush();
	}

	public void pdf417(final String data, final HttpServletResponse httpServletResponse)
			throws IOException, WriterException {
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=data-pdf417.png");

		final var outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		PDF417Writer writer = new PDF417Writer();
		BitMatrix bitMatrix = writer.encode(new ObjectMapper().writeValueAsString(data), BarcodeFormat.PDF_417, 350,
				350);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
		outputStream.flush();
	}

}
