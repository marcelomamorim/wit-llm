(function () {
  function expandPrimaryNavigation() {
    var toggles = document.querySelectorAll(
      ".md-nav--primary .md-nav__item--nested > input.md-nav__toggle"
    );

    toggles.forEach(function (toggle) {
      toggle.checked = true;
      toggle.setAttribute("checked", "");
    });
  }

  document.addEventListener("DOMContentLoaded", expandPrimaryNavigation);

  if (typeof document$ !== "undefined" && document$.subscribe) {
    document$.subscribe(expandPrimaryNavigation);
  }
})();
