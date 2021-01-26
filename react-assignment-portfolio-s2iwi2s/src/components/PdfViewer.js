
import React, { useEffect, useState, useRef, useCallback } from 'react';
import * as pdfjsLib from "pdfjs-dist/build/pdf";
import pdfjsWorker from "pdfjs-dist/build/pdf.worker.entry";
import styled from 'styled-components';
import { Link } from 'react-router-dom';
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
 width: 100px;
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
 width: 100px;
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

const PdfViewer = ({ url }) => {
 const canvasRef = useRef();
 pdfjsLib.GlobalWorkerOptions.workerSrc = pdfjsWorker

 const [pdfRef, setPdfRef] = useState();
 const [currentPage, setCurrentPage] = useState(1);

 const renderPage = useCallback((pageNum, pdf = pdfRef) => {
  pdf && pdf.getPage(pageNum).then(function (page) {
   const viewport = page.getViewport({ scale: 1.5 });
   const canvas = canvasRef.current;

   canvas.height = viewport.height;
   canvas.width = viewport.width;

   const renderContext = {
    canvasContext: canvas.getContext('2d'),
    viewport: viewport
   };
   page.render(renderContext);
  });
 }, [pdfRef]);

 useEffect(() => {
  renderPage(currentPage, pdfRef);
 }, [pdfRef, currentPage, renderPage]);

 useEffect(() => {
  const loadingTask = pdfjsLib.getDocument(url);
  loadingTask.promise.then(loadedPdf => {
   setPdfRef(loadedPdf);
  }, function (reason) {
   console.error(reason);
  });
 }, [url]);

 const nextPage = () => pdfRef && currentPage < pdfRef.numPages && setCurrentPage(currentPage + 1);

 const prevPage = () => currentPage > 1 && setCurrentPage(currentPage - 1);

 return (
  <>
   <Buttons>
    <Button onClick={prevPage}><BsFillCaretLeftFill /></Button>
    <Label>{currentPage} of {pdfRef && pdfRef.numPages}</Label>
    <Button onClick={nextPage}><BsFillCaretRightFill /></Button>
    <ButtonLink href={url} target="_blank"><BsDownload /></ButtonLink>
   </Buttons>
   <canvas ref={canvasRef} />
  </>
 );
}

export default PdfViewer