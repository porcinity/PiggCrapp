{
  description = "Initial Flake file";

  inputs = {

    flake-utils = {
      url = "github:numtide/flake-utils";
    };

    nixpkgs = {
      url = "nixpkgs/nixos-unstable";
    };

  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in {
        devShell = pkgs.mkShell {
          buildInputs = with pkgs; [
            jdk
            sbt
            coursier
          ];
        };
      });
}
