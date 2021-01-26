import styled from "styled-components"

const Content = styled.div`
 width: 100%;
 padding: 50px 0px;
`

const Template = ({ title, children }) => {
 return (
  <Content>
   <div className={"title"}>{title}</div>
   {children}
  </Content>
 )
}

export default Template