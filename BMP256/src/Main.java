import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	
	// Generic strings
	public static final String
			width_str1 = "const unsigned int width_",
			width_str2 = " = ",
			height_str1 = "const unsigned int height_",
			height_str2 = " = ";
	
	// Generic 256 color BMP palette featuring colors from RGB(0,0,0) to RGB(31,31,31) in a 8 bpp way
	public static final String
				palette_str1 = "const unsigned short int palette_",
				palette_str2 = "[] = {"
			+ "0x0000, 0x0010, 0x0200, 0x0210, 0x4000, 0x4010, 0x4200, 0x6318, 0x6378, 0x7B34, 0x0088, 0x008C, 0x0090, 0x0094, 0x0098, 0x009C," 
			+ "0x0100, 0x0104, 0x0108, 0x010C, 0x0110, 0x0114, 0x0118, 0x011C, 0x0180, 0x0184, 0x0188, 0x018C, 0x0190, 0x0194, 0x0198, 0x019C, "
			+ "0x0200, 0x0204, 0x0208, 0x020C, 0x0210, 0x0214, 0x0218, 0x021C, 0x0280, 0x0284, 0x0288, 0x028C, 0x0290, 0x0294, 0x0298, 0x029C, "
			+ "0x0300, 0x0304, 0x0308, 0x030C, 0x0310, 0x0314, 0x0318, 0x031C, 0x0380, 0x0384, 0x0388, 0x038C, 0x0390, 0x0394, 0x0398, 0x039C, "
			+ "0x2000, 0x2004, 0x2008, 0x200C, 0x2010, 0x2014, 0x2018, 0x201C, 0x2080, 0x2084, 0x2088, 0x208C, 0x2090, 0x2094, 0x2098, 0x209C, "
			+ "0x2100, 0x2104, 0x2108, 0x210C, 0x2110, 0x2114, 0x2118, 0x211C, 0x2180, 0x2184, 0x2188, 0x218C, 0x2190, 0x2194, 0x2198, 0x219C, "
			+ "0x2200, 0x2204, 0x2208, 0x220C, 0x2210, 0x2214, 0x2218, 0x221C, 0x2280, 0x2284, 0x2288, 0x228C, 0x2290, 0x2294, 0x2298, 0x229C, "
			+ "0x2300, 0x2304, 0x2308, 0x230C, 0x2310, 0x2314, 0x2318, 0x231C, 0x2380, 0x2384, 0x2388, 0x238C, 0x2390, 0x2394, 0x2398, 0x239C, "
			+ "0x4000, 0x4004, 0x4008, 0x400C, 0x4010, 0x4014, 0x4018, 0x401C, 0x4080, 0x4084, 0x4088, 0x408C, 0x4090, 0x4094, 0x4098, 0x409C, "
			+ "0x4100, 0x4104, 0x4108, 0x410C, 0x4110, 0x4114, 0x4118, 0x411C, 0x4180, 0x4184, 0x4188, 0x418C, 0x4190, 0x4194, 0x4198, 0x419C, "
			+ "0x4200, 0x4204, 0x4208, 0x420C, 0x4210, 0x4214, 0x4218, 0x421C, 0x4280, 0x4284, 0x4288, 0x428C, 0x4290, 0x4294, 0x4298, 0x429C, "
			+ "0x4300, 0x4304, 0x4308, 0x430C, 0x4310, 0x4314, 0x4318, 0x431C, 0x4380, 0x4384, 0x4388, 0x438C, 0x4390, 0x4394, 0x4398, 0x439C, "
			+ "0x6000, 0x6004, 0x6008, 0x600C, 0x6010, 0x6014, 0x6018, 0x601C, 0x6080, 0x6084, 0x6088, 0x608C, 0x6090, 0x6094, 0x6098, 0x609C, "
			+ "0x6100, 0x6104, 0x6108, 0x610C, 0x6110, 0x6114, 0x6118, 0x611C, 0x6180, 0x6184, 0x6188, 0x618C, 0x6190, 0x6194, 0x6198, 0x619C, "
			+ "0x6200, 0x6204, 0x6208, 0x620C, 0x6210, 0x6214, 0x6218, 0x621C, 0x6280, 0x6284, 0x6288, 0x628C, 0x6290, 0x6294, 0x6298, 0x629C, "
			+ "0x6300, 0x6304, 0x6308, 0x630C, 0x6310, 0x6314, 0x7BFF, 0x5294, 0x4210, 0x001F, 0x03E0, 0x03FF, 0x7C00, 0x7C1F, 0x7FE0, 0x7FFF};";
	
	// Generic strings
	public static final String pixel_str1 =
			"const unsigned char pixel_", pixel_str2 = "[] = {";
	
	// Easy byteToInt function, which masks all unsigned bytes (what masking does), shifts the bits over
	// to allow the data to be shoved down into 8 bit sections using the or operator
	// 1. 128 gets & 0xFF'ed to become 0xFF (or 255)
	// 2. (insert 3, 4 sets of 0's to represent 3 more bytes) 0000 0000 1111 1111 (256) Shove these two
	// numbers over to the last byte with << 24, whatever they may be (example is 256)
	// 3. Or the next value into the next open byte of the int using #2, and shifting by 8 bits left
	// byteToInt(1, 1, 1, 1) makes...
	// 00000001 00000001 00000001 00000001
	
	public static int byteToInt(byte a, byte b, byte c, byte d) {
		return ((a & 0xFF) << 24 | (b & 0xFF) << 16 | (c & 0xFF) << 8 | (d & 0xFF));
	}
	
	public static void main(String[] args) throws IOException {
		
		// For each given string (yay, can use multiple arguments)
		for (final String s : args) {
			
			// Gather the files and create an appropriate output name for our .h file
			final File f = new File(s), dest = new File(f.getAbsolutePath()+".h");
			final String name = f.getName().replace('.', '_'); // whatever.bmp > whatever_bmp
			
			// Reserve space in ram for our palette, which is ignored, but needed
			final byte[] fh = new byte[54];
			final byte[] palette = new byte[1024]; // always 1024 bytes
			
			final FileInputStream fis = new FileInputStream(f);
			
			fis.read(fh);
			fis.read(palette); // the palette is right after the BMP header, so you need to read or skip
			
			// coresponding bytes to the width and height of the image
			int width = byteToInt(fh[21], fh[20], fh[19], fh[18]);
			int height = byteToInt(fh[25], fh[24], fh[23], fh[22]);
			
			// Reserve space in ram for our image pixel data, and read it
			final byte[] image = new byte[width * height];
			
			fis.read(image);
			fis.close(); // It would be better to load the whole file into ram, since
			// you have to do two reads, cpu operations, then another read... gross code, but works
			
			// Force file creation, know what you are doing or go home
			dest.createNewFile();
			
			// We have to flip the image, because BMP files are top down, ass up
			final byte[] image_flip = new byte[width * height];

			// Just a simple negative Y inverse, using the cool extra argument to ++ inv
			int inv = 0;
			for (int y=height-1; y>-1; y--, inv++) {
				for (int x=0; x<width; x++)
					image_flip[y * height + x] = image[inv * height + x];
			}
			
			// We need to output 0xabcdef hexadecimals, so reserve string space pointers
			final String[] image_flip_str = new String[image_flip.length];
			
			// iterate and "0x" + hex + ", "; so make our list
			// Integer.toHexString is accurate, but don't forget the 0xFF (java signed bytes fml)
			for (int i=0; i<image_flip.length; i++)
				image_flip_str[i] = "0x" + Integer.toHexString(image_flip[i] & 0xFF) + ", ";
			
			// Open dest file for writing
			final FileOutputStream fos = new FileOutputStream(dest);
			
			// Write stuff out in a pretty sequential order
			fos.write(width_str1.getBytes());
			fos.write(name.getBytes());
			fos.write(width_str2.getBytes());
			fos.write(String.valueOf(width).getBytes());
			fos.write(';'); // EOF width
			fos.write('\n');
			fos.write(height_str1.getBytes());
			fos.write(name.getBytes());
			fos.write(height_str2.getBytes());
			fos.write(String.valueOf(height).getBytes());
			fos.write(';'); // EOF height
			fos.write('\n');
			fos.write(palette_str1.getBytes());
			fos.write(name.getBytes());
			fos.write(palette_str2.getBytes()); // EOF palette
			fos.write('\n');
			fos.write(pixel_str1.getBytes());
			fos.write(name.getBytes());
			fos.write(pixel_str2.getBytes());
			for (int i=0; i<image_flip_str.length; i++)
				fos.write(image_flip_str[i].getBytes());
			fos.write('}');
			fos.write(';'); // EOF pixel data
			fos.write('\n');
			
			fos.flush(); // flush this baby out and close 'er up
			fos.close();
		}
		
	}
	
}
