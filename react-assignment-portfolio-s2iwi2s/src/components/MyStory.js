import styled from "styled-components"
import StoryLine from "./MyStoryLine"
import Template from "./Template"

const Content = styled.div`
display: flex;
flex-direction: column;

line-height: 145%;
text-align: justify; 
`

const MyStory = () => {
  return (
    <Template title="my story">
      <Content>
        <StoryLine direction="left" image="img/me.jpg">
          <p>My name is Winston Pidor. I was born and raised in the southern part of the Philippines. In the last 3 years of my high school, I moved to my grandfather in the province and continued my high school. This is where I first learned computers. GWbasic was our programming language.</p>
          <p>Playing darts is my favorite pastime. Everyday after school and or during Saturdays and sundays, I worked in my grandfather's wholesale retail store. After work, before the sun goes down, I rush home and ride my uncle’s racer bike.</p>
          <p>After I graduated highschool, I then moved back to my parents in the city for college and graduated Computer Engineering.</p>
        </StoryLine>

        <StoryLine direction="right" image="img/best_friends.jpg">
          <p>I love animals. I owned dogs and a ball python. Akira is a Belgian Malinois. Tigger is a crossbreed of Mastiff and Great Dane. Pupcake is a Pitbull. And, Bacon is the Ball Python.</p>
        </StoryLine>

        <StoryLine direction="left" image="img/sports.jpg">
          <p>
            Besides doing Java and computers, I love to go on an adventure. I already climb the highest mountain in the Philippines. I love scuba diving, spelunking, kayaking and biking. My favorite sports is Dart.
          </p>
        </StoryLine>

        <StoryLine direction="right" image="img/dole.jpg">
          <p>On August 2000, I worked in CDR. A dutch company. We were trained as a java swing developer and developed standalone applications which later migrated to servlets and applets. I was deployed and selected as team lead to Dole company and developed web application using struts 1, javascript and html for their HR department. </p>
          <p>I went to several companies after CDR. I first learned spring framework in Innovagency(May 2005), a swiss company. Then on May 2009, I went to St Lukes Medial Center, I worked as senior programmer. We developed hospital systems using spring and hibernate.</p>
          <p>On November 2010, I moved to Verifone, a USA company. I spent most of my developer years. They were using Broadvision that uses struts 1 and Spring framework. This is where I learned to integrate different systems using BPEL, SOAP and Spring rest. </p>
        </StoryLine>

        <StoryLine direction="left" image="img/mem.jpg">
          <p>On August 2018, my family and I migrated to USA. I joined Cook Systems, Inc. on April 2019 and was deployed to FedEx. On November 2020, I joined Cook System's FastTrack'D training to upgrade and learn advanced coding skills from more skilled web developers.</p>
        </StoryLine>
      </Content>


    </Template >
  )
}

export default MyStory