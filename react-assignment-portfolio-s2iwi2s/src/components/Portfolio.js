import PortfolioLine from "./PortfolioLine"
import Template from "./Template"

const Portfolio = () => {
 const list = [{
  title: 'Dashboard project',
  description: 'A Dashboard UI demo project with draggable icons and windows using springboot, bootstrap, gridstack, jquery, jquery-ui and lodash in WebLogic server 12.2.1.3.0',
  github: 'https://github.com/s2iwi2s/dashboard',
  img: './img/dashboard.png'
 }, {
  title: 'Sara Project',
  description: 'A School Payment System demo project is using Springboot with Spring security(JWT), Mongodb and React-MaterialsUI. This project is based on Crud App Gen project that I created but is upgraded and modularized maven project with core, service, web and security',
  github: 'https://github.com/s2iwi2s/sara_proj',
  demo: 'https://sara-proj.herokuapp.com',
  img: './img/sara.png'
 }, {
  title: 'Barangay Project',
  description: 'A Population Survey Assesment project using Springboot with Spring security(JWT), Mongodb and React-MaterialsUI. This project is also based on Crud App Gen project.',
  github: 'https://github.com/s2iwi2s/brgy_proj',
  demo: 'https://brgy-proj.herokuapp.com',
  img: './img/brgy.png'
 }, {
  title: 'Crud App Gen',
  description: 'This is a crud generator for react and angular using springboot and oracle. This project will generate client ui for angular and react, crud spring rest controller, crud services, entities and repositories.',
  github: 'https://github.com/s2iwi2s/appgen-angularreact-crud',
  img: './img/crud.png'
 }, {
  title: 'Social Media Assignment',
  description: 'FastTrack\'D - Second Assessment  - Implementing a Twitter like RESTful API using Spring Boot, JPA, and Postgresql',
  github: 'https://github.com/fasttrackd-student-work/spring-assessment-social-media-optimize-prime'
 }, {
  title: 'JS Assignment Copy Page',
  description: 'Tasked with copying the Mac tech specs website saved in the MacBook Pro - Technical Specifications in HTML and CSS',
  github: 'https://github.com/fasttrackd-student-work/js-assignment-copy-page-s2iwi2s',
  img: './img/copy.png'
 }, {
  title: 'React Assignment - Porting Dev Duel',
  description: 'Recreate Dev Duel assignment using React frontend. Utilizing the server from their previous project, students shall recreate the client from scratch as a single-page application (SPA) with React',
  github: 'https://github.com/fasttrackd-student-work/js-assignment-copy-page-s2iwi2s',
  img: './img/dev-duel.png'
 }]

 return (
  <Template title="portfolio">
   {list.map(({ img, title, description, github, demo }, index) => (
    <PortfolioLine key={index} img={img} title={title} description={description} github={github} demo={demo} />
   ))}
  </Template>
 )
}

export default Portfolio