/**
 * 
 */

$(function(){
  $('.tabcontent > div').hide();
  $('.middlePageTOPLabel a').click(function () {
    $('.tabcontent > div').hide().filter(this.hash).show();
    $('.middlePageTOPLabel a').removeClass('active');
    $(this).addClass('active');
    return false;
  }).filter(':eq(0)').click();
  });
