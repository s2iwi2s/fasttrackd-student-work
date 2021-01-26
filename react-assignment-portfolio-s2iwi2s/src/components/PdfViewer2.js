import React, { useState } from 'react';
import { pdfjs, Document, Page } from 'react-pdf';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { BsFillCaretLeftFill, BsFillCaretRightFill, BsDownload } from "react-icons/bs";

const Buttons = styled.div`
display: grid;
grid: 50px / auto auto auto auto;
grid-gap: 10px;
justify-content: center;
padding: 10px 0px;
`

const Button = styled(Link)`
 border-radius: 30px;
 color: rgb(189,189,189);
 font-size: 90%;
 padding: 15px 5px;
 text-align: center;
 transition: 0.2s;
 width: 80px;
 &:hover {
  color: #555;
  background-color: #e67e22;
 }
`
const ButtonLink = styled.a`
 border-radius: 30px;
 color: rgb(189,189,189);
 font-size: 90%;
 margin-left: 15px;
 padding: 15px 5px;
 text-decoration: none;
 transition: 0.2s;
 text-align: center;
 width: 80px;
 &:hover {
  color: #555;
  background-color: #e67e22;
 }
`
const Label = styled.div`
 padding: 10px 5px;
 color: rgb(189,189,189);
 letter-spacing: 3px;
`
const PdfViewer2 = ({ url }) => {

 pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;

 const [numPages, setNumPages] = useState(null);
 const [pageNumber, setPageNumber] = useState(1);

 function onDocumentLoadSuccess({ numPages }) {
  setNumPages(numPages);
 }

 const prevPage = () => setPageNumber(pageNumber <= 1 ? numPages : pageNumber - 1);
 const nextPage = () => setPageNumber(pageNumber < numPages ? pageNumber + 1 : 1);

 return (
  <>
   <Buttons>
    <Button onClick={prevPage}><BsFillCaretLeftFill /></Button>
    <Label>{pageNumber} of {numPages}</Label>
    <Button onClick={nextPage}><BsFillCaretRightFill /></Button>
    <ButtonLink href={url} target="_blank"><BsDownload /></ButtonLink>
   </Buttons>

   <Document
    renderMode={'svg'}
    file={url}
    onLoadSuccess={onDocumentLoadSuccess}
   >
    <Page pageNumber={pageNumber} />
   </Document>
  </>
 );
}

export default PdfViewer2;