using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Web;

namespace Deneme
{
    public class QrCode
    {
       private QRCoder.QRCodeGenerator qRCodeGenerator;
        private Bitmap fotoQrCode;
        public QrCode()
        {
            qRCodeGenerator= new QRCoder.QRCodeGenerator();
        }
        public void qrCodeGenerate(string text)
        {
            QRCoder.QRCodeData qrData = qRCodeGenerator.CreateQrCode(text, QRCoder.QRCodeGenerator.ECCLevel.H);
            QRCoder.QRCode qrCode = new QRCoder.QRCode(qrData);
            qrCodeSave(qrCode);
        }
        private void qrCodeSave(QRCoder.QRCode qrCode)
        {
            fotoQrCode = qrCode.GetGraphic(150);
            fotoQrCode.Save(@"C:\Users\gurka\source\repos\Deneme\Deneme\Content\Images\image.bmp");
        }
        public Bitmap getFotoQrCode()
        {
            return fotoQrCode;
        }

    }
}