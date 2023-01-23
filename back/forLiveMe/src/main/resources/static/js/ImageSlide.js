window.onload = function() {
    const moveImage =  document.querySelector('#moveImage');
    const imageSlider = moveImage.querySelector('.imageSlider');
    const slideList = imageSlider.querySelectorAll('li')
    const moveButton = moveImage.querySelector('.imageSliderArrow');

    /* 클론 */
    const clone1 = slideList[0].cloneNode(true);
    const cloneLast = slideList[slideList.length - 1].cloneNode(true);
    imageSlider.insertBefore(cloneLast, slideList[0]);
    imageSlider.appendChild(clone1);

    /* 주요 변수 초기화 */
    let currentIdx = 0;
    let translate = 0;
    const speedTime = 500;

    /* CSSOM 셋업 */
    const sliderCloneList = imageSlider.querySelectorAll('li');
    const liWidth = slideList[0].clientWidth;
    const sliderWidth = liWidth * sliderCloneList.length;
    imageSlider.style.width = `${sliderWidth}px`;
    currentIdx = 1;
    translate = -liWidth;
    imageSlider.style.transform = `translateX(${translate}px)`

    /* 리스너 설치하기 */
    moveButton.addEventListener('click', moveSlide);

    /* 슬라이드 실행 */
    function move(D) {
        currentIdx += (-1 * D);
        translate += liWidth * D;
        imageSlider.style.transform = `translateX(${translate}px)`;
        imageSlider.style.transition = `all ${speedTime}ms ease`
    }

    /* 클릭 버튼 */
    function moveSlide(event) {
        event.preventDefault();
        console.log(event.target.className);
        if (event.target.className.includes('next')) {
            move(-1);
            if (currentIdx === sliderCloneList.length -1)
                setTimeout(() => {
                    imageSlider.style.transition = 'none';
                    currentIdx = 1;
                    translate = -liWidth;
                    imageSlider.style.transform = `translateX(${translate}px)`;
                }, speedTime);
        } else {
            move(1);
            if (currentIdx === 0) {
                setTimeout(() => {
                    imageSlider.style.transition = 'none';
                    currentIdx = sliderCloneList.length -2;
                    translate = -(liWidth * currentIdx);
                    imageSlider.style.transform = `translateX(${translate}px)`;
                }, speedTime);
            }
        }
    }

    function sliding() {
        move(-1);
        if (currentIdx === sliderCloneList.length -1)
            setTimeout(() => {
                imageSlider.style.transition = 'none';
                currentIdx = 1;
                translate = -liWidth;
                imageSlider.style.transform = `translateX(${translate}px)`;
            }, speedTime);
    }

    function showSliding() {
        setInterval(sliding, 10000);
    }

    showSliding();
}
