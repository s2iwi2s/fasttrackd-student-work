import styled from "styled-components"
import PdfViewer from "./PdfViewer2"
import Template from "./Template"

const Content = styled.div`
display: block;
text-align:center;
`

const Resume = () => {
  return (
    <Template title="resume">
      <Content>
        <PdfViewer url='docs/resume.pdf' />
      </Content>
    </Template>
  )
}

export default Resume